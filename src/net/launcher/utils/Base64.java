package net.launcher.utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Base64 {

	public static Encoder getEncoder() {
		return new Encoder();
	}

	public static Decoder getDecoder() {
		return new Decoder();
	}

	public static class Encoder {

		private static final char[] toBase64 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
				'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
				'4', '5', '6', '7', '8', '9', '+', '/' };

		private final int outLength(int srclen) {
			int len = 0;
			int n = srclen % 3;
			len = 4 * (srclen / 3) + (n == 0 ? 0 : n + 1);
			return len;
		}

		public byte[] encode(byte[] src) {
			int len = outLength(src.length);
			byte[] dst = new byte[len];
			int ret = encode0(src, 0, src.length, dst);
			if (ret != dst.length)
				return Arrays.copyOf(dst, ret);
			return dst;
		}

		private int encode0(byte[] src, int off, int end, byte[] dst) {
			int sp = off;
			int slen = (end - off) / 3 * 3;
			int sl = off + slen;
			int dp = 0;
			while (sp < sl) {
				int sl0 = Math.min(sp + slen, sl);
				for (int sp0 = sp, dp0 = dp; sp0 < sl0;) {
					int bits = (src[sp0++] & 0xff) << 16 | (src[sp0++] & 0xff) << 8 | (src[sp0++] & 0xff);
					dst[dp0++] = (byte) toBase64[(bits >>> 18) & 0x3f];
					dst[dp0++] = (byte) toBase64[(bits >>> 12) & 0x3f];
					dst[dp0++] = (byte) toBase64[(bits >>> 6) & 0x3f];
					dst[dp0++] = (byte) toBase64[bits & 0x3f];
				}
				int dlen = (sl0 - sp) / 3 * 4;
				dp += dlen;
				sp = sl0;
			}
			if (sp < end) {
				int b0 = src[sp++] & 0xff;
				dst[dp++] = (byte) toBase64[b0 >> 2];
				if (sp == end) {
					dst[dp++] = (byte) toBase64[(b0 << 4) & 0x3f];
				} else {
					int b1 = src[sp++] & 0xff;
					dst[dp++] = (byte) toBase64[(b0 << 4) & 0x3f | (b1 >> 4)];
					dst[dp++] = (byte) toBase64[(b1 << 2) & 0x3f];
				}
			}
			return dp;
		}
	}

	public static class Decoder {

		private static final int[] fromBase64 = new int[256];
		static {
			Arrays.fill(fromBase64, -1);
			for (int i = 0; i < Encoder.toBase64.length; i++)
				fromBase64[Encoder.toBase64[i]] = i;
			fromBase64['='] = -2;
		}

		public byte[] decode(byte[] src) {
			byte[] dst = new byte[outLength(src, 0, src.length)];
			int ret = decode0(src, 0, src.length, dst);
			if (ret != dst.length) {
				dst = Arrays.copyOf(dst, ret);
			}
			return dst;
		}

		public byte[] decode(String src) {
			return decode(src.getBytes(StandardCharsets.ISO_8859_1));
		}

		private int outLength(byte[] src, int sp, int sl) {
			int paddings = 0;
			int len = sl - sp;
			if (len == 0)
				return 0;
			if (src[sl - 1] == '=') {
				paddings++;
				if (src[sl - 2] == '=')
					paddings++;
			}
			if (paddings == 0 && (len & 0x3) != 0)
				paddings = 4 - (len & 0x3);
			return 3 * ((len + 3) / 4) - paddings;
		}

		private int decode0(byte[] src, int sp, int sl, byte[] dst) {
			int dp = 0;
			int bits = 0;
			int shiftto = 18;
			while (sp < sl) {
				int b = src[sp++] & 0xff;
				if ((b = fromBase64[b]) < 0) {
					if (b == -2) {
						break;
					}
				}
				bits |= (b << shiftto);
				shiftto -= 6;
				if (shiftto < 0) {
					dst[dp++] = (byte) (bits >> 16);
					dst[dp++] = (byte) (bits >> 8);
					dst[dp++] = (byte) (bits);
					shiftto = 18;
					bits = 0;
				}
			}
			if (shiftto == 6) {
				dst[dp++] = (byte) (bits >> 16);
			} else if (shiftto == 0) {
				dst[dp++] = (byte) (bits >> 16);
				dst[dp++] = (byte) (bits >> 8);
			} else if (shiftto == 12) {
				throw new IllegalArgumentException("Last unit does not have enough valid bits");
			}
			return dp;
		}
	}
}