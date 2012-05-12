package pt.isel.pdm.yamba.TwitterAsync.helpers;

import android.os.Parcel;
import android.os.Parcelable;

public class StatusContainer implements Parcelable{
	
	public static final Parcelable.Creator<StatusContainer> CREATOR = new Parcelable.Creator<StatusContainer>() {
		public StatusContainer createFromParcel(Parcel in) {
		    return new StatusContainer(in.readString(), in.readLong());
		}
		
		public StatusContainer[] newArray(int size) {
		    return new StatusContainer[size];
		}
	};
	
	private String _status;
	private long _inReplyTo;
	
	public StatusContainer(String status, long inReplyTo) {
		_status = status;
		_inReplyTo = inReplyTo;
	}
	
	public String getStatus() {
		return _status;
	}
	
	public long inReplyTo() {
		return _inReplyTo;
	}
	
	public boolean isReply() {
		return _inReplyTo != -1;
	}
	
	public static StatusContainer create(String status, long inReplyTo) {
		return new StatusContainer(status, inReplyTo);
	}
	
	public static StatusContainer create(String status) {
		return new StatusContainer(status, -1);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {		
		arg0.writeString(_status);
		arg0.writeLong(_inReplyTo);	
	}
}