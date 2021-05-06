package pl.lipov.laborki.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Gallery

class GalleryListAdapter(
        private val galleries: List<Gallery>,
        private val listener: OnItemClickListener
): RecyclerView.Adapter<GalleryViewHolder>() {
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gallery, parent, false)

        return GalleryViewHolder(view,listener)
    }

    override fun getItemCount(): Int = galleries.size

    override fun onBindViewHolder(
            holder: GalleryViewHolder,
            position: Int
    ) {
        val gallery = galleries[position]
        //Picasso.get().load(gallery.url).into(holder.image)
        //holder.name
        holder.name.text = gallery.name
    }

}