package es.source.code.util;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 邮件管理类
 */
public class MailManagerUtil {
        //发送账户
        private static final String SENDER_NAME = "csxcsxts@163.com";
        //发送账户的密码
        private static final String SENDER_PASS = "6906850csx";
        //邮箱服务器
        private static final String VALUE_MAIL_HOST = "smtp.163.com";
        //邮箱服务器主机
        private static final String KEY_MAIL_HOST = "mail.smtp.host";
        //邮箱是否需要鉴权
        private static final String KEY_MAIL_AUTH = "mail.smtp.auth";
        //需要鉴权
        private static final String VALUE_MAIL_AUTH = "true";

        public static MailManagerUtil getInstance() {
            return InstanceHolder.instance;
        }

        private MailManagerUtil() {
        }

        private static class InstanceHolder {
            private static MailManagerUtil instance = new MailManagerUtil();
        }

        class MailTask extends AsyncTask<Void, Void, Boolean> {

            private MimeMessage mimeMessage;

            public MailTask(MimeMessage mimeMessage) {
                this.mimeMessage = mimeMessage;
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    Transport.send(mimeMessage);
                    return Boolean.TRUE;
                } catch (MessagingException e) {
                    e.printStackTrace();
                    return Boolean.FALSE;
                }
            }
        }

        public void sendMail(final String title, final String content) {
            MimeMessage mimeMessage = createMessage(title, content);
            MailTask mailTask = new MailTask(mimeMessage);
            mailTask.execute();
        }

        public void sendMailWithFile(String title, String content, String filePath) {
            MimeMessage mimeMessage = createMessage(title, content);
            appendFile(mimeMessage, filePath);
            MailTask mailTask = new MailTask(mimeMessage);
            mailTask.execute();
        }

        public void sendMailWithMultiFile(String title, String content, List<String> pathList) {
            MimeMessage mimeMessage = createMessage(title, content);
            appendMultiFile(mimeMessage, pathList);
            MailTask mailTask = new MailTask(mimeMessage);
            mailTask.execute();
        }

        private Authenticator getAuthenticator() {
            return new Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_NAME, SENDER_PASS);
                }
            };
        }

        private MimeMessage createMessage(String title, String content) {
            //设置发送的属性
            Properties properties = System.getProperties();
            properties.put(KEY_MAIL_HOST, VALUE_MAIL_HOST);
            properties.put(KEY_MAIL_AUTH, VALUE_MAIL_AUTH);
            //获取Session
            Session session = Session.getInstance(properties, getAuthenticator());
            //构建邮件消息
            MimeMessage mimeMessage = new MimeMessage(session);
            try {
                //设置发送者
                mimeMessage.setFrom(new InternetAddress(SENDER_NAME));
                //设置接收者
                InternetAddress[] addresses = new InternetAddress[]{new InternetAddress(SENDER_NAME)};
                mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
                //设置邮件的主题
                mimeMessage.setSubject(title);
                //设置邮件的内容
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setContent(content, "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(textPart);
                mimeMessage.setContent(multipart);
                //设置发送时间
                mimeMessage.setSentDate(new Date());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return mimeMessage;
        }

        private void appendFile(MimeMessage message, String filePath) {
            try {
                Multipart multipart = (Multipart) message.getContent();
                MimeBodyPart filePart = new MimeBodyPart();
                filePart.attachFile(filePath);
                multipart.addBodyPart(filePart);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        private void appendMultiFile(MimeMessage message, List<String> pathList) {
            try {
                Multipart multipart = (Multipart) message.getContent();
                for (String path : pathList) {
                    MimeBodyPart filePart = new MimeBodyPart();
                    filePart.attachFile(path);
                    multipart.addBodyPart(filePart);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
}
