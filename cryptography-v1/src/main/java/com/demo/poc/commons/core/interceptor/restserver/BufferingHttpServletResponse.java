package com.demo.poc.commons.core.interceptor.restserver;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class BufferingHttpServletResponse extends HttpServletResponseWrapper {

  private final ByteArrayOutputStream cachedContent;
  private final ServletOutputStream outputStream;
  private final PrintWriter writer;

  public BufferingHttpServletResponse(HttpServletResponse response) {
    super(response);
    this.cachedContent = new ByteArrayOutputStream();
    this.outputStream = new CachedBodyServletOutputStream(cachedContent);
    this.writer = new PrintWriter(cachedContent, true, StandardCharsets.UTF_8);
  }

  @Override
  public ServletOutputStream getOutputStream() {
    return outputStream;
  }

  @Override
  public PrintWriter getWriter() {
    return writer;
  }

  @Override
  public void flushBuffer() throws IOException {
    super.flushBuffer();
    if (writer != null) {
      writer.flush();
    }
    if (outputStream != null) {
      outputStream.flush();
    }
  }

  public String getCachedBody() throws IOException {
    flushBuffer();
    return cachedContent.toString(StandardCharsets.UTF_8);
  }

  private static class CachedBodyServletOutputStream extends ServletOutputStream {

    private final ByteArrayOutputStream cachedContent;

    public CachedBodyServletOutputStream(ByteArrayOutputStream cachedContent) {
      this.cachedContent = cachedContent;
    }

    @Override
    public void write(int b) {
      cachedContent.write(b);
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean isReady() {
      return true;
    }
  }
}
