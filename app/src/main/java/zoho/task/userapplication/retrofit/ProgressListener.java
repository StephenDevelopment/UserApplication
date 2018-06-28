package zoho.task.userapplication.retrofit;

public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
  }