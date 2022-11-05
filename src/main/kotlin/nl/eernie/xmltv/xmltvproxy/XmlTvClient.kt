package nl.eernie.xmltv.xmltvproxy

import nl.eernie.xmltv.xsd.Tv
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name="xmltvClient", url = "https://xteve.eernie.dev/xmltv/xteve.xml")
interface XmlTvClient {

    @GetMapping
    fun getXmltv(): String

}