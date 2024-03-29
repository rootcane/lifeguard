
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.inventec.Base64Coder;

import com.xerox.amazonws.sqs.MessageQueue;
import com.xerox.amazonws.sqs.Message;
import com.xerox.amazonws.sqs.SQSUtils;

/**
 * This sample application creates a queue with the specified name (if the queue doesn't
 * already exist), and then sends (enqueues) a message to the queue.
 */
public class EnqueueSample {
    private static Log logger = LogFactory.getLog(EnqueueSample.class);

	public static void main( String[] args ) {
		try {
			if (args.length < 2) {
				logger.error("usage: EnqueueSample <queueId> <message>");
			}
			Properties props = new Properties();
			props.load(AddWork.class.getClassLoader().getResourceAsStream("aws.properties"));

			String queueName = args[0];
			String message = args[1];

			// Create the message queue object
			MessageQueue msgQueue = SQSUtils.connectToQueue(queueName,
											props.getProperty("aws.accessId"),
											props.getProperty("aws.secretKey"));
			logger.info(" url returned = "+msgQueue.getUrl());

			String msgId = msgQueue.sendMessage( Base64Coder.encodeString(message) );
			logger.info( "Sent message with id " + msgId );
		} catch ( Exception ex ) {
			logger.error( "EXCEPTION", ex );
		}
	}
}
