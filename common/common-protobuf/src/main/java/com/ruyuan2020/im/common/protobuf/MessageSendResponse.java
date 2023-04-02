// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MessageSendResponse.proto

package com.ruyuan2020.im.common.protobuf;

/**
 * Protobuf type {@code com.ruyuan2020.im.MessageSendResponse}
 */
public final class MessageSendResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.ruyuan2020.im.MessageSendResponse)
    MessageSendResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use MessageSendResponse.newBuilder() to construct.
  private MessageSendResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MessageSendResponse() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new MessageSendResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private MessageSendResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            messageId_ = input.readUInt64();
            break;
          }
          case 16: {

            chatType_ = input.readUInt32();
            break;
          }
          case 24: {

            fromId_ = input.readUInt64();
            break;
          }
          case 32: {

            toId_ = input.readUInt64();
            break;
          }
          case 40: {

            sequence_ = input.readUInt64();
            break;
          }
          case 48: {

            timestamp_ = input.readUInt64();
            break;
          }
          case 56: {

            chatId_ = input.readUInt64();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.ruyuan2020.im.common.protobuf.MessageSendResponseProto.internal_static_com_ruyuan2020_im_MessageSendResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.ruyuan2020.im.common.protobuf.MessageSendResponseProto.internal_static_com_ruyuan2020_im_MessageSendResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.ruyuan2020.im.common.protobuf.MessageSendResponse.class, com.ruyuan2020.im.common.protobuf.MessageSendResponse.Builder.class);
  }

  public static final int MESSAGEID_FIELD_NUMBER = 1;
  private long messageId_;
  /**
   * <code>uint64 messageId = 1;</code>
   * @return The messageId.
   */
  @java.lang.Override
  public long getMessageId() {
    return messageId_;
  }

  public static final int CHATTYPE_FIELD_NUMBER = 2;
  private int chatType_;
  /**
   * <code>uint32 chatType = 2;</code>
   * @return The chatType.
   */
  @java.lang.Override
  public int getChatType() {
    return chatType_;
  }

  public static final int FROMID_FIELD_NUMBER = 3;
  private long fromId_;
  /**
   * <code>uint64 fromId = 3;</code>
   * @return The fromId.
   */
  @java.lang.Override
  public long getFromId() {
    return fromId_;
  }

  public static final int TOID_FIELD_NUMBER = 4;
  private long toId_;
  /**
   * <code>uint64 toId = 4;</code>
   * @return The toId.
   */
  @java.lang.Override
  public long getToId() {
    return toId_;
  }

  public static final int SEQUENCE_FIELD_NUMBER = 5;
  private long sequence_;
  /**
   * <code>uint64 sequence = 5;</code>
   * @return The sequence.
   */
  @java.lang.Override
  public long getSequence() {
    return sequence_;
  }

  public static final int TIMESTAMP_FIELD_NUMBER = 6;
  private long timestamp_;
  /**
   * <code>uint64 timestamp = 6;</code>
   * @return The timestamp.
   */
  @java.lang.Override
  public long getTimestamp() {
    return timestamp_;
  }

  public static final int CHATID_FIELD_NUMBER = 7;
  private long chatId_;
  /**
   * <code>uint64 chatId = 7;</code>
   * @return The chatId.
   */
  @java.lang.Override
  public long getChatId() {
    return chatId_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (messageId_ != 0L) {
      output.writeUInt64(1, messageId_);
    }
    if (chatType_ != 0) {
      output.writeUInt32(2, chatType_);
    }
    if (fromId_ != 0L) {
      output.writeUInt64(3, fromId_);
    }
    if (toId_ != 0L) {
      output.writeUInt64(4, toId_);
    }
    if (sequence_ != 0L) {
      output.writeUInt64(5, sequence_);
    }
    if (timestamp_ != 0L) {
      output.writeUInt64(6, timestamp_);
    }
    if (chatId_ != 0L) {
      output.writeUInt64(7, chatId_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (messageId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(1, messageId_);
    }
    if (chatType_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(2, chatType_);
    }
    if (fromId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(3, fromId_);
    }
    if (toId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(4, toId_);
    }
    if (sequence_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(5, sequence_);
    }
    if (timestamp_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(6, timestamp_);
    }
    if (chatId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(7, chatId_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.ruyuan2020.im.common.protobuf.MessageSendResponse)) {
      return super.equals(obj);
    }
    com.ruyuan2020.im.common.protobuf.MessageSendResponse other = (com.ruyuan2020.im.common.protobuf.MessageSendResponse) obj;

    if (getMessageId()
        != other.getMessageId()) return false;
    if (getChatType()
        != other.getChatType()) return false;
    if (getFromId()
        != other.getFromId()) return false;
    if (getToId()
        != other.getToId()) return false;
    if (getSequence()
        != other.getSequence()) return false;
    if (getTimestamp()
        != other.getTimestamp()) return false;
    if (getChatId()
        != other.getChatId()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + MESSAGEID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getMessageId());
    hash = (37 * hash) + CHATTYPE_FIELD_NUMBER;
    hash = (53 * hash) + getChatType();
    hash = (37 * hash) + FROMID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getFromId());
    hash = (37 * hash) + TOID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getToId());
    hash = (37 * hash) + SEQUENCE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getSequence());
    hash = (37 * hash) + TIMESTAMP_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTimestamp());
    hash = (37 * hash) + CHATID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getChatId());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.ruyuan2020.im.common.protobuf.MessageSendResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.ruyuan2020.im.common.protobuf.MessageSendResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.ruyuan2020.im.common.protobuf.MessageSendResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.ruyuan2020.im.common.protobuf.MessageSendResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.ruyuan2020.im.common.protobuf.MessageSendResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.ruyuan2020.im.common.protobuf.MessageSendResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.ruyuan2020.im.common.protobuf.MessageSendResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.ruyuan2020.im.common.protobuf.MessageSendResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.ruyuan2020.im.common.protobuf.MessageSendResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.ruyuan2020.im.common.protobuf.MessageSendResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.ruyuan2020.im.common.protobuf.MessageSendResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.ruyuan2020.im.common.protobuf.MessageSendResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.ruyuan2020.im.common.protobuf.MessageSendResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.ruyuan2020.im.MessageSendResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.ruyuan2020.im.MessageSendResponse)
      com.ruyuan2020.im.common.protobuf.MessageSendResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.ruyuan2020.im.common.protobuf.MessageSendResponseProto.internal_static_com_ruyuan2020_im_MessageSendResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.ruyuan2020.im.common.protobuf.MessageSendResponseProto.internal_static_com_ruyuan2020_im_MessageSendResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.ruyuan2020.im.common.protobuf.MessageSendResponse.class, com.ruyuan2020.im.common.protobuf.MessageSendResponse.Builder.class);
    }

    // Construct using com.ruyuan2020.im.common.protobuf.MessageSendResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      messageId_ = 0L;

      chatType_ = 0;

      fromId_ = 0L;

      toId_ = 0L;

      sequence_ = 0L;

      timestamp_ = 0L;

      chatId_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.ruyuan2020.im.common.protobuf.MessageSendResponseProto.internal_static_com_ruyuan2020_im_MessageSendResponse_descriptor;
    }

    @java.lang.Override
    public com.ruyuan2020.im.common.protobuf.MessageSendResponse getDefaultInstanceForType() {
      return com.ruyuan2020.im.common.protobuf.MessageSendResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.ruyuan2020.im.common.protobuf.MessageSendResponse build() {
      com.ruyuan2020.im.common.protobuf.MessageSendResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.ruyuan2020.im.common.protobuf.MessageSendResponse buildPartial() {
      com.ruyuan2020.im.common.protobuf.MessageSendResponse result = new com.ruyuan2020.im.common.protobuf.MessageSendResponse(this);
      result.messageId_ = messageId_;
      result.chatType_ = chatType_;
      result.fromId_ = fromId_;
      result.toId_ = toId_;
      result.sequence_ = sequence_;
      result.timestamp_ = timestamp_;
      result.chatId_ = chatId_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.ruyuan2020.im.common.protobuf.MessageSendResponse) {
        return mergeFrom((com.ruyuan2020.im.common.protobuf.MessageSendResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.ruyuan2020.im.common.protobuf.MessageSendResponse other) {
      if (other == com.ruyuan2020.im.common.protobuf.MessageSendResponse.getDefaultInstance()) return this;
      if (other.getMessageId() != 0L) {
        setMessageId(other.getMessageId());
      }
      if (other.getChatType() != 0) {
        setChatType(other.getChatType());
      }
      if (other.getFromId() != 0L) {
        setFromId(other.getFromId());
      }
      if (other.getToId() != 0L) {
        setToId(other.getToId());
      }
      if (other.getSequence() != 0L) {
        setSequence(other.getSequence());
      }
      if (other.getTimestamp() != 0L) {
        setTimestamp(other.getTimestamp());
      }
      if (other.getChatId() != 0L) {
        setChatId(other.getChatId());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.ruyuan2020.im.common.protobuf.MessageSendResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.ruyuan2020.im.common.protobuf.MessageSendResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long messageId_ ;
    /**
     * <code>uint64 messageId = 1;</code>
     * @return The messageId.
     */
    @java.lang.Override
    public long getMessageId() {
      return messageId_;
    }
    /**
     * <code>uint64 messageId = 1;</code>
     * @param value The messageId to set.
     * @return This builder for chaining.
     */
    public Builder setMessageId(long value) {
      
      messageId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 messageId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearMessageId() {
      
      messageId_ = 0L;
      onChanged();
      return this;
    }

    private int chatType_ ;
    /**
     * <code>uint32 chatType = 2;</code>
     * @return The chatType.
     */
    @java.lang.Override
    public int getChatType() {
      return chatType_;
    }
    /**
     * <code>uint32 chatType = 2;</code>
     * @param value The chatType to set.
     * @return This builder for chaining.
     */
    public Builder setChatType(int value) {
      
      chatType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 chatType = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearChatType() {
      
      chatType_ = 0;
      onChanged();
      return this;
    }

    private long fromId_ ;
    /**
     * <code>uint64 fromId = 3;</code>
     * @return The fromId.
     */
    @java.lang.Override
    public long getFromId() {
      return fromId_;
    }
    /**
     * <code>uint64 fromId = 3;</code>
     * @param value The fromId to set.
     * @return This builder for chaining.
     */
    public Builder setFromId(long value) {
      
      fromId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 fromId = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearFromId() {
      
      fromId_ = 0L;
      onChanged();
      return this;
    }

    private long toId_ ;
    /**
     * <code>uint64 toId = 4;</code>
     * @return The toId.
     */
    @java.lang.Override
    public long getToId() {
      return toId_;
    }
    /**
     * <code>uint64 toId = 4;</code>
     * @param value The toId to set.
     * @return This builder for chaining.
     */
    public Builder setToId(long value) {
      
      toId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 toId = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearToId() {
      
      toId_ = 0L;
      onChanged();
      return this;
    }

    private long sequence_ ;
    /**
     * <code>uint64 sequence = 5;</code>
     * @return The sequence.
     */
    @java.lang.Override
    public long getSequence() {
      return sequence_;
    }
    /**
     * <code>uint64 sequence = 5;</code>
     * @param value The sequence to set.
     * @return This builder for chaining.
     */
    public Builder setSequence(long value) {
      
      sequence_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 sequence = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearSequence() {
      
      sequence_ = 0L;
      onChanged();
      return this;
    }

    private long timestamp_ ;
    /**
     * <code>uint64 timestamp = 6;</code>
     * @return The timestamp.
     */
    @java.lang.Override
    public long getTimestamp() {
      return timestamp_;
    }
    /**
     * <code>uint64 timestamp = 6;</code>
     * @param value The timestamp to set.
     * @return This builder for chaining.
     */
    public Builder setTimestamp(long value) {
      
      timestamp_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 timestamp = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearTimestamp() {
      
      timestamp_ = 0L;
      onChanged();
      return this;
    }

    private long chatId_ ;
    /**
     * <code>uint64 chatId = 7;</code>
     * @return The chatId.
     */
    @java.lang.Override
    public long getChatId() {
      return chatId_;
    }
    /**
     * <code>uint64 chatId = 7;</code>
     * @param value The chatId to set.
     * @return This builder for chaining.
     */
    public Builder setChatId(long value) {
      
      chatId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 chatId = 7;</code>
     * @return This builder for chaining.
     */
    public Builder clearChatId() {
      
      chatId_ = 0L;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:com.ruyuan2020.im.MessageSendResponse)
  }

  // @@protoc_insertion_point(class_scope:com.ruyuan2020.im.MessageSendResponse)
  private static final com.ruyuan2020.im.common.protobuf.MessageSendResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.ruyuan2020.im.common.protobuf.MessageSendResponse();
  }

  public static com.ruyuan2020.im.common.protobuf.MessageSendResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MessageSendResponse>
      PARSER = new com.google.protobuf.AbstractParser<MessageSendResponse>() {
    @java.lang.Override
    public MessageSendResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new MessageSendResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MessageSendResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MessageSendResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.ruyuan2020.im.common.protobuf.MessageSendResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
