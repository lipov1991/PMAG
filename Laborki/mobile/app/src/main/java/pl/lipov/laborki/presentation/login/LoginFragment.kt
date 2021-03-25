package pl.lipov.laborki.presentation.login

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.FragmentLoginBinding
import pl.lipov.laborki.presentation.MainActivity


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var loginCallback: LoginCallback? = null

    private var starAnimator: ValueAnimator? = null
    private var borderedStarAnimator: ValueAnimator? = null

    lateinit var ACTIVITY: MainActivity

    private val screenUnlockKey =
        mutableListOf(
            Event.DOUBLE_TAP.toString(),
            Event.DOUBLE_TAP.toString(),
            Event.LONG_CLICK.toString(),
            Event.ACCELERATION_CHANGE.toString()
        )

    override fun onAttach(
        context: Context
    ) {
        super.onAttach(context)
        ACTIVITY = context as MainActivity

        if (context is LoginCallback) {
            loginCallback = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)
        var counter: Int = 1

        borderedStarAnimator = binding.icBorderedStar.wrongPasswordAnimation().apply {
            doOnStart {
                binding.loginButton.isEnabled = false
            }
            doOnEnd {
                binding.icStar.visibility = View.VISIBLE
            }
        }

        starAnimator = binding.icStar.getTintAnimator(duration = 1000).apply {
            doOnStart {
                binding.loginButton.isEnabled = false
            }
            doOnEnd {
                // starAnimator?.start()
                binding.loginButton.isEnabled = true
            }
        }


        binding.loginButton.setOnClickListener {
            loginCallback?.onLoginSuccess()
        }

        binding.loginButton.setOnClickListener {

            if ((ACTIVITY.loginKeys.elementAtOrNull(0) == screenUnlockKey.elementAtOrNull(0))
                && (ACTIVITY.loginKeys.elementAtOrNull(1) == screenUnlockKey.elementAtOrNull(1))
                && (ACTIVITY.loginKeys.elementAtOrNull(2) == screenUnlockKey.elementAtOrNull(2))
                && (ACTIVITY.loginKeys.elementAtOrNull(3) == screenUnlockKey.elementAtOrNull(3)))
            {
                Toast.makeText(context, "Zalogowano", Toast.LENGTH_SHORT).show()
                starAnimator?.start()

            }

            else if (counter != 3) {
                borderedStarAnimator?.start()
                counter = counter?.plus(1)
                Toast.makeText(context, "Nieudane logowanie", Toast.LENGTH_SHORT).show()
                binding.loginButton.isEnabled = true
            }
            else if (counter == 3) {
                borderedStarAnimator?.start()
                Toast.makeText(context, "3 nieudane próby logowania - blokada", Toast.LENGTH_SHORT).show()
                binding.loginButton.isEnabled = false

            }

        }
    }


    private fun View.getTintAnimator(
        duration: Long = 500,
        @ColorRes firstColorResId: Int = R.color.gold,
        @ColorRes secondColorResId: Int = R.color.grey
    ): ValueAnimator {
        return ValueAnimator.ofArgb(
            getColor(context, firstColorResId),
            getColor(context, secondColorResId)
        ).apply {
            addUpdateListener {
                setTint(background, it.animatedValue as Int)
            }
            this.duration = duration
        }
    }



    private fun View.wrongPasswordAnimation(
        duration: Long = 500,
        @ColorRes firstColorResId: Int = R.color.error,
        @ColorRes secondColorResId: Int = R.color.grey
    ): ValueAnimator {
        return ValueAnimator.ofArgb(
            getColor(context, firstColorResId),
            getColor(context, secondColorResId)
        ).apply {
            addUpdateListener {
                setTint(background, it.animatedValue as Int)
            }
            this.duration = duration
        }
    }

}

