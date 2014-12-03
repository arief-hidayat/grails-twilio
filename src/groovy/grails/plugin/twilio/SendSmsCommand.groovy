package grails.plugin.twilio

@grails.validation.Validateable
class SendSmsCommand {
    String destination
    String message

    static constraints = {
        message(size: 1..140)
    }
}