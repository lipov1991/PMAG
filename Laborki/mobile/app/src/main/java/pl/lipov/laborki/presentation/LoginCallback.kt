package pl.lipov.laborki.presentation

interface LoginCallback {
    fun onLoginSuccess()
    fun onLoginFail()
    fun onLoginLock()

}