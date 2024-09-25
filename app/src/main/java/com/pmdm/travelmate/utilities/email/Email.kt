package com.pmdm.travelmate.utilities.email


import com.pmdm.travelmate.models.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.Multipart
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

object Email {
    private val smtpConfig = Properties()
    private val mailSession: Session
    private val mailMessage: Message

    init {
        smtpConfig["mail.from"] = "travelmate.balmis@gmail.com"
        smtpConfig["mail.fromtext"] = "TravelMate"
        smtpConfig["mail.smtp.username"] = "travelmate.balmis@gmail.com"
        smtpConfig["mail.smtp.password"] = "rmrt vqwc zbhb ejnw"
        smtpConfig["mail.smtp.host"] = "smtp.gmail.com"
        smtpConfig["mail.smtp.port"] = "587"
        smtpConfig["mail.smtp.auth"] = "true"
        smtpConfig["mail.smtp.starttls.enable"] = "true"

        mailSession = Session.getInstance(smtpConfig, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication? {
                return PasswordAuthentication(
                    smtpConfig.getProperty("mail.smtp.username"),
                    smtpConfig.getProperty("mail.smtp.password")
                )
            }
        })

        mailMessage = MimeMessage(mailSession)
        mailMessage.setFrom(
            InternetAddress(
                smtpConfig.getProperty("mail.from"),
                smtpConfig.getProperty("mail.fromtext")
            )
        )
    }

    private fun generateCode(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..6)
            .map { allowedChars.random() }
            .joinToString("")
    }

    // Funcíon que envía código de recuperación vía Email
    fun sendEmailPassReset(usuario: Usuario): String { // Le llega el usuario al cual va a enviar el email.
        val multipart: Multipart =
            MimeMultipart() // Crea un Multipart, para añadir las partes que necesitemos del mail.
        val htmlPart = MimeBodyPart() // Parte que añade el HTML a envíar.
        val code = generateCode() // Llama a la función que genera un código aleatorio.
        CoroutineScope(Dispatchers.IO).launch {// Inicia una corutina para el envío del email.
            htmlPart.setText(
                "A continuación le dejamos su código para cambiar la contraseña: <strong style=\"font-family: 'Courier New', monospace;\">${code}</strong>",
                "utf-8",
                "html"
            ) // HTML con el formato del email.
            multipart.addBodyPart(htmlPart) // Añade la parte html al email
            mailMessage.setContent(multipart) // Añade todo el contenido al cuerpo del email.

            setSubject("TravelMate - Código restauración contraseña") // Indica el asunto del mail.
            setRecipient(usuario.email) // Obtiene el mail del usuario, extrayendo su propiedad.
            Transport.send(mailMessage) // Envía el email.
        }
        return code // Devuelve el código que luego sera verificado por el que ha introducido el usuario.
    }

    fun sendEmailWelcome(usuario: Usuario) {
        val multipart: Multipart = MimeMultipart()
        val htmlPart = MimeBodyPart()

        CoroutineScope(Dispatchers.IO).launch {
            htmlPart.setText(

                "<h1>Bienvenido a TravelMate</h1>\n" +
                        "<p>¡Gracias por registrarte en nuestra aplicación de viajes! Estamos emocionados de tenerte como parte de nuestra comunidad.</p>\n" +
                        "<p>Con TravelMate, podrás descubrir destinos increíbles, y planificar tus viajes de manera sencilla.</p>\n",
                "utf-8",
                "html"
            )
            multipart.addBodyPart(htmlPart)
            mailMessage.setContent(multipart)

            setSubject("TravelMate - Bienvenido")
            setRecipient(usuario.email)
            Transport.send(mailMessage)
        }
    }


    private fun setSubject(subject: String) {
        mailMessage.subject = subject
    }

    private fun setRecipient(recipient: String) {
        val toEmailAddresses = InternetAddress.parse(recipient)
        mailMessage.setRecipients(Message.RecipientType.TO, toEmailAddresses)
    }

}
