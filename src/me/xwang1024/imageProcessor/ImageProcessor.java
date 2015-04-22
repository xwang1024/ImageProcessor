package me.xwang1024.imageProcessor;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class ImageProcessor {
	static float[] elements = { 0.0f, -1.0f, 0.0f, -1.0f, 4f, -1.0f, 0.0f, -1.0f, 0.0f };

	static int threshold = 0x222222;

	public static BufferedImage process(BufferedImage raw) throws Exception {
		BufferedImage img = new BufferedImage(raw.getWidth(), raw.getHeight(), raw.getType());
		Kernel kernel = new Kernel(3, 3, elements);
		ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		cop.filter(raw, img);
		int area = 0;
		int[] stx = new int[raw.getWidth()];
		int[] sty = new int[raw.getHeight()];
		for (int i = 0; i < raw.getWidth(); i++) {
			for (int j = 0; j < raw.getHeight(); j++) {
				if ((img.getRGB(i, j) & 0x00FFFFFF) > threshold) {
					img.setRGB(i, j, 0xffff0000);
					System.out.println(i + " " + j);
					stx[i]++;
					sty[j]++;
				}
			}
		}

		BufferedImage statImg = new BufferedImage(raw.getWidth(), raw.getHeight(), raw.getType());
		System.out.println(raw.getWidth() + "*" + raw.getHeight());
		// for(int i = 0; i < stx.length; i++) {
		// System.out.println(i + "-" + stx[i]);
		// statImg.setRGB(i, raw.getHeight()-stx[i], 0xffffffff);
		// }
		for (int j = 0; j < sty.length; j++) {
			System.out.println(raw.getHeight() - sty[j] + "-" + j);
			statImg.setRGB(sty[j] - 1, j, 0xffffffff);
		}
		System.out.println((float) area / (float) raw.getWidth() / (float) raw.getHeight());
		return statImg;
	}

	public static RGB decodeRGB(int color, int colorType) throws Exception {
		// System.out.println("decodeRGB: " + Integer.toHexString(color));
		RGB rgb = new RGB();
		switch (colorType) {
		case BufferedImage.TYPE_3BYTE_BGR:
			rgb.r = (color & 0x000000FF);
			rgb.g = (color & 0x0000FF00) >> 8;
			rgb.b = (color & 0x00FF0000) >> 16;
			break;
		default:
			throw new Exception("Unsupported colorType");
		}
		return rgb;
	}

	public static int encodeRGB(RGB color, int colorType) throws Exception {
		int rgb = 0;
		switch (colorType) {
		case BufferedImage.TYPE_3BYTE_BGR:
			rgb = color.r | (color.g << 8) | (color.b << 16) | (0xFF000000);
			break;
		default:
			throw new Exception("Unsupported colorType");
		}
		// System.out.println("encodeRGB: " + Integer.toHexString(rgb));
		return rgb;
	}
}

class RGB {
	int r;
	int g;
	int b;

	@Override
	public String toString() {
		return "RGB [r=" + r + ", g=" + g + ", b=" + b + "]";
	}

}
