package com.cloud.project.vision;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageSource;

public class TextDetector {

	/**
	 * Detects text in the specified remote image on Google Cloud Storage.
	 *
	 * @param gcsPath
	 *            The path to the remote file on Google Cloud Storage to detect text
	 *            in.
	 * @param out
	 *            A {@link PrintStream} to write the detected text to.
	 * @throws Exception
	 *             on errors while closing the client.
	 * @throws IOException
	 *             on Input/Output errors.
	 */
	public static String detectTextGcs(String gcsPath) throws Exception, IOException {
		List<AnnotateImageRequest> requests = new ArrayList<>();

		ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
		Image img = Image.newBuilder().setSource(imgSource).build();
		Feature feat = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();
		AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);

		StringBuilder carLicense = new StringBuilder();

		try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					carLicense.append("Error: " + res.getError().getMessage());
					return carLicense.toString();
				}

				// For full list of available annotations, see http://g.co/cloud/vision/docs
				for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
					carLicense.append("Text: " + annotation.getDescription());
				}
			}
		}
		return carLicense.toString();
	}
}
