// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MessageSendResponse.proto

package com.ruyuan2020.im.common.protobuf;

public interface MessageSendResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.ruyuan2020.im.MessageSendResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>uint64 messageId = 1;</code>
   * @return The messageId.
   */
  long getMessageId();

  /**
   * <code>uint32 chatType = 2;</code>
   * @return The chatType.
   */
  int getChatType();

  /**
   * <code>uint64 fromId = 3;</code>
   * @return The fromId.
   */
  long getFromId();

  /**
   * <code>uint64 toId = 4;</code>
   * @return The toId.
   */
  long getToId();

  /**
   * <code>uint64 sequence = 5;</code>
   * @return The sequence.
   */
  long getSequence();

  /**
   * <code>uint64 timestamp = 6;</code>
   * @return The timestamp.
   */
  long getTimestamp();

  /**
   * <code>uint64 chatId = 7;</code>
   * @return The chatId.
   */
  long getChatId();
}
