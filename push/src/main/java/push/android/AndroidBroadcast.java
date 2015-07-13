package push.android;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import push.AndroidNotification;

public class AndroidBroadcast extends AndroidNotification
{
	private static Logger log = LoggerFactory.getLogger(AndroidBroadcast.class);

	public AndroidBroadcast()
	{
		try
		{
			this.setPredefinedKeyValue("type", "broadcast");
		} catch (Exception ex)
		{
			log.error(ex.getLocalizedMessage(), ex);
		}
	}
}
