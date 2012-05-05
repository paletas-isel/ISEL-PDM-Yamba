package pt.isel.pdm.Yamba.ViewModel;

import java.util.Calendar;
import java.util.Date;

import winterwell.jtwitter.Twitter.Status;

import android.os.Parcel;
import android.os.Parcelable;

public class Tweet implements Parcelable{

	private long _id;
	private String _author;
	private String _status;
	private Date _publication;
	
	private Tweet(long id, String author, String status, Date publication) {
		_id = id;
		_author = author;
		_status = status;
		_publication = publication;
	}
	
	private Tweet(Parcel in) {
		_id = in.readLong();
		_author = in.readString();
		_status = in.readString();
		_publication = new Date(in.readString());
	}
	
	public static Tweet createFrom(Status status) {
		return new Tweet(status.getId(), status.getUser().getName(), status.getText(), status.getCreatedAt());
	}
	
	public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
		public Tweet createFromParcel(Parcel in) {
		    return new Tweet(in);
		}
		
		public Tweet[] newArray(int size) {
		    return new Tweet[size];
		}
	};
	
	public long getId() {
		return _id;
	}
	
	public String getAuthor() {
		return _author;
	}
	
	public String getStatus() {
		return _status;
	}
	
	public String getStatusSample(int size) {
		if(_status.length() > size)
			return String.format("%s...", _status.substring(0, size));
		return _status;
	}
	
	public Date getPublicationDate() {
		return _publication;
	}
	
	public Date getElapsedTime() {
		long publicationMillis = _publication.getTime();
		long currentDateMillis = Calendar.getInstance().getTimeInMillis();
		
		return new Date(currentDateMillis - publicationMillis);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		arg0.writeLong(_id);
		arg0.writeString(_author);
		arg0.writeString(_status);
		arg0.writeString(_publication.toString());		
	}

}
