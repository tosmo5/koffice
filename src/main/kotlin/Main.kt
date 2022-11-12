import com.tosmo.koffice.annotations.Sheet
import com.tosmo.koffice.proxy.KtWorkbook
import kotlin.reflect.KClass

@Sheet("Demo", 1)
internal data class Demo(
    val id: Long,
    val name: String
)

internal class DemoBook(filePath: String) : KtWorkbook(filePath) {
    override val entities: List<KClass<*>>
        get() = listOf(Demo::class)
}

internal fun main() {
    val resPath = "src/main/resources/demo.xlsx"
    DemoBook(resPath).read(Demo::class) {
        println(it.toString())
    }
}