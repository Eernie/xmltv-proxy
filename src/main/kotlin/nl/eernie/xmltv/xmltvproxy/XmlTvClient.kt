package nl.eernie.xmltv.xmltvproxy

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "xmltvClient", url = "\${xmltv.client.url}")
interface XmlTvClient {

    @GetMapping
    fun getXmltv(): String

}