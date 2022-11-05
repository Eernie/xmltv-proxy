package nl.eernie.xmltv.xmltvproxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class XmltvProxyApplication

fun main(args: Array<String>) {
    runApplication<XmltvProxyApplication>(*args)
}
