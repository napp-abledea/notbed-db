/**
 *
 */
package test.com.notbed.database;

import java.util.Date;

import com.notbed.database.impl.Entity;

/**
 * @author Alexandru Bledea
 * @since Sep 22, 2013
 */
public class ChatEntry extends Entity {

	private String message;
	private String author;
	private String edited;
	private Date time;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the edited
	 */
	public String getEdited() {
		return edited;
	}

	/**
	 * @param edited the edited to set
	 */
	public void setEdited(String edited) {
		this.edited = edited;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ChatEntry [message=" + message + ", author=" + author + ", edited=" + edited + ", time=" + time + "]";
	}

}
