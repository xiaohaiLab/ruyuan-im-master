// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MessagePush.proto

package com.ruyuan2020.im.common.protobuf;

public final class MessagePushProto {
  private MessagePushProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_ruyuan2020_im_MessagePush_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_ruyuan2020_im_MessagePush_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021MessagePush.proto\022\021com.ruyuan2020.im\"\257" +
      "\001\n\013MessagePush\022\020\n\010chatType\030\001 \001(\r\022\021\n\tmess" +
      "ageId\030\002 \001(\004\022\017\n\007content\030\003 \001(\t\022\020\n\010sequence" +
      "\030\004 \001(\004\022\021\n\ttimestamp\030\005 \001(\004\022\016\n\006chatId\030\006 \001(" +
      "\004\022\016\n\006fromId\030\007 \001(\004\022\020\n\010memberId\030\010 \001(\004\022\023\n\013m" +
      "essageType\030\t \001(\rB7\n!com.ruyuan2020.im.co" +
      "mmon.protobufB\020MessagePushProtoP\001b\006proto" +
      "3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_ruyuan2020_im_MessagePush_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_ruyuan2020_im_MessagePush_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_ruyuan2020_im_MessagePush_descriptor,
        new java.lang.String[] { "ChatType", "MessageId", "Content", "Sequence", "Timestamp", "ChatId", "FromId", "MemberId", "MessageType", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}