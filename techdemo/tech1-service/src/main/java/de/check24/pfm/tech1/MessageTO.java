package de.check24.pfm.tech1;

import io.nats.client.Message;

import java.time.LocalTime;
import java.util.UUID;

/**
 * @author jan.kopcsek
 */
public class MessageTO {
    private String id;
    private String timestamp;
    private String subject;
    private String replyTo;
    private String direction;
    private String content;

    public MessageTO(String id, String timestamp, String subject, String replyTo, String direction, String content) {
        this.id = id;
        this.timestamp = timestamp;
        this.subject = subject;
        this.replyTo = replyTo;
        this.direction = direction;
        this.content = content;
    }

    public MessageTO() {
    }

    public static MessageTO fromNutsMessage(Message msg) {
        if (msg == null) {
            return null;
        }
        MessageTO result = new MessageTO();
        result.setId(UUID.randomUUID().toString());
        result.setTimestamp(LocalTime.now().toString());
        result.setSubject(msg.getSubject());
        result.setReplyTo(msg.getReplyTo());
        result.setDirection(">");
        result.setContent(new String(msg.getData()));
        return result;
    }

    public String getId() {
        return this.id;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getReplyTo() {
        return this.replyTo;
    }

    public String getDirection() {
        return this.direction;
    }

    public String getContent() {
        return this.content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MessageTO)) return false;
        final MessageTO other = (MessageTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$timestamp = this.getTimestamp();
        final Object other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !this$timestamp.equals(other$timestamp)) return false;
        final Object this$subject = this.getSubject();
        final Object other$subject = other.getSubject();
        if (this$subject == null ? other$subject != null : !this$subject.equals(other$subject)) return false;
        final Object this$replyTo = this.getReplyTo();
        final Object other$replyTo = other.getReplyTo();
        if (this$replyTo == null ? other$replyTo != null : !this$replyTo.equals(other$replyTo)) return false;
        final Object this$direction = this.getDirection();
        final Object other$direction = other.getDirection();
        if (this$direction == null ? other$direction != null : !this$direction.equals(other$direction)) return false;
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MessageTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $timestamp = this.getTimestamp();
        result = result * PRIME + ($timestamp == null ? 43 : $timestamp.hashCode());
        final Object $subject = this.getSubject();
        result = result * PRIME + ($subject == null ? 43 : $subject.hashCode());
        final Object $replyTo = this.getReplyTo();
        result = result * PRIME + ($replyTo == null ? 43 : $replyTo.hashCode());
        final Object $direction = this.getDirection();
        result = result * PRIME + ($direction == null ? 43 : $direction.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        return result;
    }

    public String toString() {
        return "MessageTO(id=" + this.getId() + ", timestamp=" + this.getTimestamp() + ", subject=" + this.getSubject() + ", replyTo=" + this.getReplyTo() + ", direction=" + this.getDirection() + ", content=" + this.getContent() + ")";
    }
}
