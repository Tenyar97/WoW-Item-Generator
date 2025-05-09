package com.tenyar97.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class PngMetadataReader
	{

		public static String readComment(File pngFile) throws IOException
			{
				try (ImageInputStream iis = ImageIO.createImageInputStream(pngFile))
					{
						Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("png");
						if (!readers.hasNext())
							{
								throw new IllegalStateException("No PNG ImageReader found");
							}

						ImageReader reader = readers.next();
						reader.setInput(iis, true);
						IIOMetadata metadata = reader.getImageMetadata(0);
						Node root = metadata.getAsTree("javax_imageio_png_1.0");
						return extractCommentFromMetadata(root);
					}
			}

		private static String extractCommentFromMetadata(Node root)
			{
				Node textNode = findNode(root, "tEXt");
				if (textNode != null)
					{
						for (Node child = textNode.getFirstChild(); child != null; child = child.getNextSibling())
							{
								if ("tEXtEntry".equals(child.getNodeName()))
									{
										NamedNodeMap attributes = child.getAttributes();
										Node keywordAttr = attributes.getNamedItem("keyword");
										Node valueAttr = attributes.getNamedItem("value");

										if (keywordAttr != null && "Title".equals(keywordAttr.getNodeValue())
												&& valueAttr != null)
											{
												return valueAttr.getNodeValue();
											}
									}
							}
					}
				return null;
			}

		private static Node findNode(Node parent, String name)
			{
				for (Node node = parent.getFirstChild(); node != null; node = node.getNextSibling())
					{
						if (name.equals(node.getNodeName()))
							{
								return node;
							}
					}
				return null;
			}
	}
