// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RegisterRequest.proto

package com.ruyuan2020.im.common.protobuf;

/**
 * Protobuf type {@code com.ruyuan2020.im.RegisterRequest}
 */
public final class RegisterRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.ruyuan2020.im.RegisterRequest)
    RegisterRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use RegisterRequest.newBuilder() to construct.
  private RegisterRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private RegisterRequest() {
    serverId_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new RegisterRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private RegisterRequest(
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
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            serverId_ = s;
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
    return com.ruyuan2020.im.common.protobuf.RegisterRequestProto.internal_static_com_ruyuan2020_im_RegisterRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.ruyuan2020.im.common.protobuf.RegisterRequestProto.internal_static_com_ruyuan2020_im_RegisterRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.ruyuan2020.im.common.protobuf.RegisterRequest.class, com.ruyuan2020.im.common.protobuf.RegisterRequest.Builder.class);
  }

  public static final int SERVERID_FIELD_NUMBER = 1;
  private volatile java.lang.Object serverId_;
  /**
   * <code>string serverId = 1;</code>
   * @return The serverId.
   */
  @java.lang.Override
  public java.lang.String getServerId() {
    java.lang.Object ref = serverId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      serverId_ = s;
      return s;
    }
  }
  /**
   * <code>string serverId = 1;</code>
   * @return The bytes for serverId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getServerIdBytes() {
    java.lang.Object ref = serverId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      serverId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(serverId_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, serverId_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(serverId_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, serverId_);
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
    if (!(obj instanceof com.ruyuan2020.im.common.protobuf.RegisterRequest)) {
      return super.equals(obj);
    }
    com.ruyuan2020.im.common.protobuf.RegisterRequest other = (com.ruyuan2020.im.common.protobuf.RegisterRequest) obj;

    if (!getServerId()
        .equals(other.getServerId())) return false;
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
    hash = (37 * hash) + SERVERID_FIELD_NUMBER;
    hash = (53 * hash) + getServerId().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.ruyuan2020.im.common.protobuf.RegisterRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.ruyuan2020.im.common.protobuf.RegisterRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.ruyuan2020.im.common.protobuf.RegisterRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.ruyuan2020.im.common.protobuf.RegisterRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.ruyuan2020.im.common.protobuf.RegisterRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.ruyuan2020.im.common.protobuf.RegisterRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.ruyuan2020.im.common.protobuf.RegisterRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.ruyuan2020.im.common.protobuf.RegisterRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.ruyuan2020.im.common.protobuf.RegisterRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.ruyuan2020.im.common.protobuf.RegisterRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.ruyuan2020.im.common.protobuf.RegisterRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.ruyuan2020.im.common.protobuf.RegisterRequest parseFrom(
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
  public static Builder newBuilder(com.ruyuan2020.im.common.protobuf.RegisterRequest prototype) {
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
   * Protobuf type {@code com.ruyuan2020.im.RegisterRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.ruyuan2020.im.RegisterRequest)
      com.ruyuan2020.im.common.protobuf.RegisterRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.ruyuan2020.im.common.protobuf.RegisterRequestProto.internal_static_com_ruyuan2020_im_RegisterRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.ruyuan2020.im.common.protobuf.RegisterRequestProto.internal_static_com_ruyuan2020_im_RegisterRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.ruyuan2020.im.common.protobuf.RegisterRequest.class, com.ruyuan2020.im.common.protobuf.RegisterRequest.Builder.class);
    }

    // Construct using com.ruyuan2020.im.common.protobuf.RegisterRequest.newBuilder()
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
      serverId_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.ruyuan2020.im.common.protobuf.RegisterRequestProto.internal_static_com_ruyuan2020_im_RegisterRequest_descriptor;
    }

    @java.lang.Override
    public com.ruyuan2020.im.common.protobuf.RegisterRequest getDefaultInstanceForType() {
      return com.ruyuan2020.im.common.protobuf.RegisterRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.ruyuan2020.im.common.protobuf.RegisterRequest build() {
      com.ruyuan2020.im.common.protobuf.RegisterRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.ruyuan2020.im.common.protobuf.RegisterRequest buildPartial() {
      com.ruyuan2020.im.common.protobuf.RegisterRequest result = new com.ruyuan2020.im.common.protobuf.RegisterRequest(this);
      result.serverId_ = serverId_;
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
      if (other instanceof com.ruyuan2020.im.common.protobuf.RegisterRequest) {
        return mergeFrom((com.ruyuan2020.im.common.protobuf.RegisterRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.ruyuan2020.im.common.protobuf.RegisterRequest other) {
      if (other == com.ruyuan2020.im.common.protobuf.RegisterRequest.getDefaultInstance()) return this;
      if (!other.getServerId().isEmpty()) {
        serverId_ = other.serverId_;
        onChanged();
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
      com.ruyuan2020.im.common.protobuf.RegisterRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.ruyuan2020.im.common.protobuf.RegisterRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object serverId_ = "";
    /**
     * <code>string serverId = 1;</code>
     * @return The serverId.
     */
    public java.lang.String getServerId() {
      java.lang.Object ref = serverId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        serverId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string serverId = 1;</code>
     * @return The bytes for serverId.
     */
    public com.google.protobuf.ByteString
        getServerIdBytes() {
      java.lang.Object ref = serverId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        serverId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string serverId = 1;</code>
     * @param value The serverId to set.
     * @return This builder for chaining.
     */
    public Builder setServerId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      serverId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string serverId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearServerId() {
      
      serverId_ = getDefaultInstance().getServerId();
      onChanged();
      return this;
    }
    /**
     * <code>string serverId = 1;</code>
     * @param value The bytes for serverId to set.
     * @return This builder for chaining.
     */
    public Builder setServerIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      serverId_ = value;
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


    // @@protoc_insertion_point(builder_scope:com.ruyuan2020.im.RegisterRequest)
  }

  // @@protoc_insertion_point(class_scope:com.ruyuan2020.im.RegisterRequest)
  private static final com.ruyuan2020.im.common.protobuf.RegisterRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.ruyuan2020.im.common.protobuf.RegisterRequest();
  }

  public static com.ruyuan2020.im.common.protobuf.RegisterRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RegisterRequest>
      PARSER = new com.google.protobuf.AbstractParser<RegisterRequest>() {
    @java.lang.Override
    public RegisterRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new RegisterRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<RegisterRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RegisterRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.ruyuan2020.im.common.protobuf.RegisterRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

