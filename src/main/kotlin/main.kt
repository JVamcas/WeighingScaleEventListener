
import gnu.io.CommPortIdentifier
import java.util.*


fun main(args: Array<String>) {

    val portIdentifiers: Enumeration<CommPortIdentifier> = CommPortIdentifier.getPortIdentifiers() as Enumeration<CommPortIdentifier>
    val scale = WeighingScaleEventListener("COM1")
}