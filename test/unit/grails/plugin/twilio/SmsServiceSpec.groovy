package grails.plugin.twilio

import grails.test.mixin.TestFor
import grails.twilio.SmsService
import grails.util.Holders
import groovyx.net.http.AuthConfig
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import spock.lang.Specification


/**
 * Created by arief.hidayat on 03/12/2014.
 */
@TestFor(SmsService)
class SmsServiceSpec extends Specification {
    void "test without proxy"() {
        setup:
        String sid = "sid"
        String authToken = "authtoken"
        String phone = "5555551212"

        Holders.config.twilio.proxy = false
        Holders.config.twilio.account.sid = sid
        Holders.config.twilio.account.auth_token = authToken
        Holders.config.twilio.phones.main = phone


        HTTPBuilder httpBuilder = Mock()
        httpBuilder.getAuth() >> {
            AuthConfig authConfig = Mock()
            1 * authConfig.basic(sid, authToken) // expect to be authenticated once using correct sid and auth token
            authConfig
        }
        service.twilioHttpEndpointBean = httpBuilder

        when:
        service.send(phone, "halo")

        then:
        1 * httpBuilder.request(Method.POST, _ as Closure) // expect to post the request once to Twilio
    }
}