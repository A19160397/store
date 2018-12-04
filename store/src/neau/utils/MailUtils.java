package neau.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {

	public static void sendMail(String email, String emailMsg)
			throws AddressException, MessagingException {
		// 1.鍒涘缓涓�涓▼搴忎笌閭欢鏈嶅姟鍣ㄤ細璇濆璞� Session

		Properties props = new Properties();
		//璁剧疆鍙戦�佺殑鍗忚
		props.setProperty("mail.transport.protocol", "SMTP");
		
		//璁剧疆鍙戦�侀偖浠剁殑鏈嶅姟鍣�
		props.setProperty("mail.host", "localhost");
		props.setProperty("mail.smtp.auth", "true");// 鎸囧畾楠岃瘉涓簍rue

		// 鍒涘缓楠岃瘉鍣�
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				//璁剧疆鍙戦�佷汉鐨勫笎鍙峰拰瀵嗙爜
				return new PasswordAuthentication("service", "123");
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.鍒涘缓涓�涓狹essage锛屽畠鐩稿綋浜庢槸閭欢鍐呭
		Message message = new MimeMessage(session);

		//璁剧疆鍙戦�佽��
		message.setFrom(new InternetAddress("service@store.com"));

		//璁剧疆鍙戦�佹柟寮忎笌鎺ユ敹鑰�
		message.setRecipient(RecipientType.TO, new InternetAddress(email)); 

		//璁剧疆閭欢涓婚
		message.setSubject("鐢ㄦ埛婵�娲�");
		 
		//璁剧疆閭欢鍐呭
		message.setContent(emailMsg, "text/html;charset=utf-8");

		// 3.鍒涘缓 Transport鐢ㄤ簬灏嗛偖浠跺彂閫�
		Transport.send(message);
	}
}
