package email;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import data.Module;
import data.Student;
import file.MyFile;

/**
 * Created by Ramazan Cinardere on 06.09.15
 */
public class MyEmail{

    ////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private Context context = null;

    private Properties properties = null;

    private Session session = null;

    private String host     = "booklibrary.project@gmail.com",
                   password = "booklibrary12345";

    private Student student = null;

    private MyFile myFile = null;

    ////////////////////////////
    //       Constructor      //
    ////////////////////////////

    public MyEmail(Context context) {
        this.context = context;
        initAttributes();
        initProperties();
        signIn();
    }



    ////////////////////////////
    //         Methods        //
    ////////////////////////////

    public void sendEmail(String mailTo, String title, String content) {
        new SendEmailTask().execute(mailTo, title, content);
    }

    public void sendEmail(String mailTo, String title, Module module) {

        String content = "<h><img src='http://fs1.directupload.net/images/150906/ripjxqck.jpg'><br>"
                +"<p><b>Pr&uuml;fung erfolgreich angemeldet</b> <br><br>"
                +"<p>Hallo " +student.getName() + ",<br>"
                +"du hast dich erfolgreich f&uuml;r die Pr&uuml;fung angemeldet. <br>"
                +"<p>Modul: " +module.getTitle() + "<br>"
                +"<p>Datum: " +module.getDateOfExam() + "<br>"
                +"<p>Uhrzeit: " +module.getTimeOfExam() + "<br>"
                +"<br><br>"
                +"Viel Erfolg bei der Klausur!!";


        mailTo = mailTo.replace("\\s","");

        new SendEmailTask().execute(mailTo, title, content);
    }

    private void initAttributes() {

        if(myFile == null) {
            myFile = new MyFile(context);
        }

        student = (Student) myFile.getObjectFromFile(context.getString(R.string.file_studentSer));
    }



    private void initProperties() {
        properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
    }

    private void signIn() {

        try {
            session = Session.getInstance(properties,new javax.mail.Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(host, password);
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("signIn()"," " +e.getCause() + " " +e.getMessage());
        }
    }

    ////////////////////
    ///   SUBCLASS   //
    //////////////////


    private class SendEmailTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            send(params[0],params[1],params[2]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(context, context.getString(R.string.cofirmationSend), Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }


        public boolean send(String mailTo, String subject, String content) {

            try {

                Message message = new MimeMessage(session);

                message.setFrom(new InternetAddress(host));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
                message.setSubject(subject);
                message.setContent(content,"text/html");

                Transport.send(message);


                return true;
            }catch (Exception e) {
                e.printStackTrace();
            }

            return false;

        }


    }





}
