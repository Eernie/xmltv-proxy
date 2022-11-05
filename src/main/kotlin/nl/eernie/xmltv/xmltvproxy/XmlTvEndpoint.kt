package nl.eernie.xmltv.xmltvproxy

import nl.eernie.xmltv.xsd.Tv
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.StringReader
import java.io.StringWriter
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.xml.bind.JAXBContext


@RestController
class XmlTvEndpoint(val xmlTvClient: XmlTvClient, @Value("\${TZ}") val timezone: String) {

    @RequestMapping(value = ["/xmltv"], produces = [MediaType.APPLICATION_XML_VALUE])
    fun getXmlTv(): ResponseEntity<String> {

        val jaxbContext: JAXBContext = JAXBContext.newInstance(Tv::class.java)
        val unmarshaller = jaxbContext.createUnmarshaller()
        val tv = unmarshaller.unmarshal(StringReader(xmlTvClient.getXmltv())) as Tv

        tv.programme.forEach {
            it.start = adjustTimezone(it.start)
            it.stop = adjustTimezone(it.stop)
        }

        val marshaller = jaxbContext.createMarshaller()
        val stringWriter = StringWriter()
        marshaller.marshal(tv, stringWriter)

        return ResponseEntity(stringWriter.toString(), HttpStatus.OK)
    }

    fun adjustTimezone(date: String): String {
        val pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss Z")
        val zdt = ZonedDateTime.parse(date, pattern)
        return zdt.withZoneSameLocal(ZoneId.of(timezone)).format(pattern)
    }
}