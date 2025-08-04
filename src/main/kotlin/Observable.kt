interface Observable {
    val observers: MutableList<ScreenBufferObserver>
    fun notifyObservers()
    fun subscribe(observer: ScreenBufferObserver)
    fun unsubscribe(observer: ScreenBufferObserver)
}