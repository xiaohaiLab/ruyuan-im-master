// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Result.proto

package com.ruyuan2020.im.common.protobuf;

public interface ResultOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.ruyuan2020.im.Result)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bool success = 1;</code>
   * @return The success.
   */
  boolean getSuccess();

  /**
   * <code>string errorCode = 2;</code>
   * @return The errorCode.
   */
  java.lang.String getErrorCode();
  /**
   * <code>string errorCode = 2;</code>
   * @return The bytes for errorCode.
   */
  com.google.protobuf.ByteString
      getErrorCodeBytes();

  /**
   * <code>string errorMessage = 3;</code>
   * @return The errorMessage.
   */
  java.lang.String getErrorMessage();
  /**
   * <code>string errorMessage = 3;</code>
   * @return The bytes for errorMessage.
   */
  com.google.protobuf.ByteString
      getErrorMessageBytes();

  /**
   * <code>bytes data = 4;</code>
   * @return The data.
   */
  com.google.protobuf.ByteString getData();
}
