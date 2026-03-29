package io.suhan.sjpt.client;

public interface SjptClient {
    void init() throws Exception;
    String getGradCertHistory() throws Exception;
    String getAcademicRecord() throws Exception;
}
