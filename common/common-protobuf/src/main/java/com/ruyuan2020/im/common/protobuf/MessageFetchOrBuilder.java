// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MessageFetch.proto

package com.ruyuan2020.im.common.protobuf;

public interface MessageFetchOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.ruyuan2020.im.MessageFetch)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>uint64 chatId = 1;</code>
   * @return The chatId.
   */
  long getChatId();

  /**
   * <code>uint64 toId = 2;</code>
   * @return The toId.
   */
  long getToId();

  /**
   * <code>uint32 chatType = 3;</code>
   * @return The chatType.
   */
  int getChatType();
}