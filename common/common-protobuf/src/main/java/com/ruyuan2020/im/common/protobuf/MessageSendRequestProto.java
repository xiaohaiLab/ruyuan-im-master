// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MessageSendRequest.proto

package com.ruyuan2020.im.common.protobuf;

public final class MessageSendRequestProto {
  private MessageSendRequestProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_ruyuan2020_im_MessageSendRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_ruyuan2020_im_MessageSendRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\030MessageSendRequest.proto\022\021com.ruyuan20" +
      "20.im\"\262\001\n\022MessageSendRequest\022\021\n\tmessageI" +
      "d\030\001 \001(\004\022\020\n\010chatType\030\002 \001(\r\022\016\n\006fromId\030\003 \001(" +
      "\004\022\014\n\004toId\030\004 \001(\004\022\016\n\006chatId\030\005 \001(\004\022\023\n\013messa" +
      "geType\030\006 \001(\r\022\017\n\007content\030\007 \001(\t\022\020\n\010sequenc" +
      "e\030\010 \001(\004\022\021\n\ttimestamp\030\t \001(\004B>\n!com.ruyuan" +
      "2020.im.common.protobufB\027MessageSendRequ" +
      "estProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_ruyuan2020_im_MessageSendRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_ruyuan2020_im_MessageSendRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_ruyuan2020_im_MessageSendRequest_descriptor,
        new java.lang.String[] { "MessageId", "ChatType", "FromId", "ToId", "ChatId", "MessageType", "Content", "Sequence", "Timestamp", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
