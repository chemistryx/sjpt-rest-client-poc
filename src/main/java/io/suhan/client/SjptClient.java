package io.suhan.client;

public interface SjptClient {
    void init() throws Exception;
    String getGradCertHistory() throws Exception;
    String getAcademicRecord() throws Exception;
}
