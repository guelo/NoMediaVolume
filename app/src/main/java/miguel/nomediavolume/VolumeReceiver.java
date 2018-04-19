package miguel.nomediavolume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;


public class VolumeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

		Log.d("Volume", intent.getAction());
		int volume = (Integer)intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
//		int streammute = (Integer)intent.getExtras().get("android.media.STREAM_MUTE_CHANGED_ACTION");
		int alias = (Integer)intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_TYPE_ALIAS");
		int inttype = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE",-1);
		String type = getTypeString(inttype);
		String typeAlias = getTypeString(alias);
		int pev_volume = (Integer)intent.getExtras().get("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE");
		Log.d("Volume", volume+  " type="+type + " alias="+typeAlias);

		if (inttype == AudioManager.STREAM_RING) {
			int maxRing = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
			int maxMedia = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			int newVolume = (int)(((float)maxMedia/maxRing) * volume);
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);
		}

	}

	private String getTypeString(int inttype) {
		switch (inttype) {
			case AudioManager.STREAM_VOICE_CALL:
				return "STREAM_VOICE_CALL";
			case AudioManager.STREAM_ALARM:
				return "STREAM_ALARM";
			case AudioManager.STREAM_SYSTEM:
				return "STREAM_SYSTEM";
			case AudioManager.STREAM_RING:
				return "STREAM_RING";
			case AudioManager.STREAM_MUSIC:
				return "STREAM_MUSIC";
			case AudioManager.STREAM_DTMF:
				return "STREAM_DTMF";
			case AudioManager.STREAM_NOTIFICATION:
				return "STREAM_NOTIFICATION";
		}
		return inttype+"";
	}
}
