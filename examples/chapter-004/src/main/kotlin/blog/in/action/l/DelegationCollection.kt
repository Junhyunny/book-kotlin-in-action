package blog.`in`.action.l

//class DelegationCollection<T>(
//    private val innerList: Collection<T> = arrayListOf()
//) : Collection<T> {
//    override val size: Int = innerList.size
//    override fun isEmpty(): Boolean = innerList.isEmpty()
//    override fun iterator(): Iterator<T> = innerList.iterator()
//    override fun containsAll(elements: Collection<T>): Boolean = innerList.containsAll(elements)
//    override fun contains(element: T): Boolean = innerList.contains(element)
//}

class DelegationCollection<T>(
    private val innerList: Collection<T> = arrayListOf()
) : Collection<T> by innerList