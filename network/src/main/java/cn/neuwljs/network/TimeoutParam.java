package cn.neuwljs.network;

public class TimeoutParam {

    private long connectTime;
    private long readTime;
    private long writeTime;

    private TimeoutParam(Builder builder) {
        connectTime = builder.connectTime;
        readTime = builder.readTime;
        writeTime = builder.writeTime;
    }

    public long getConnectTime() {
        return connectTime;
    }

    public long getReadTime() {
        return readTime;
    }

    public long getWriteTime() {
        return writeTime;
    }

    public static final class Builder {
        private long connectTime;
        private long readTime;
        private long writeTime;

        public Builder() {
        }

        public Builder connectTime(long val) {
            connectTime = val;
            return this;
        }

        public Builder readTime(long val) {
            readTime = val;
            return this;
        }

        public Builder writeTime(long val) {
            writeTime = val;
            return this;
        }

        public TimeoutParam build() {
            return new TimeoutParam (this);
        }
    }
}
