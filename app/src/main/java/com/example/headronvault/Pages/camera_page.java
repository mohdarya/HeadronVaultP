package com.example.headronvault.Pages;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.*;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.headronvault.API.APIClient;
import com.example.headronvault.API.APIInterface;
import com.example.headronvault.API.ApiMessage;
import com.example.headronvault.API.card_id_fetch_data;
import com.example.headronvault.API.image_message;
import com.example.headronvault.R;
import com.example.headronvault.camera_pageDirections;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.hardware.camera2.CameraCaptureSession.*;
import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link camera_page.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link camera_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class camera_page extends Fragment
{


    CameraDevice cameraDevice;
    ArrayList<String> fileNames = new ArrayList<>();
    String cameraID;
    private ImageReader imageReader;
    TextureView camera_texture;
    Size imageDimentions;
    private APIInterface apiInterface;
    CaptureRequest.Builder captureBuilder;
    CameraCaptureSession cameraSession;
    CaptureRequest request;
    Handler backGroundHandler;
    CameraCaptureSession.CaptureCallback callback;
    TextureView.SurfaceTextureListener surfaceTextureListener;
    HandlerThread handlerThread;
    final static int CAMERA_CODE = 001;
    static View view;
    private OnFragmentInteractionListener mListener;

    public camera_page()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment camera_page.
     */
    // TODO: Rename and change types and number of parameters
    public static camera_page newInstance(String param1, String param2)
    {
        camera_page fragment = new camera_page();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        setView();
        camera_texture = (TextureView) getActivity().findViewById(R.id.page_camera_preview_texture);
        callback = new CaptureCallback()
        {
            @Override
            public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber)
            {
                super.onCaptureStarted(session, request, timestamp, frameNumber);
            }
        };
        camera_texture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                takePicture();


            }
        });
        surfaceTextureListener = new TextureView.SurfaceTextureListener()
        {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height)
            {
                try
                {

                        openCamera();

                } catch (CameraAccessException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height)
            {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface)
            {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface)
            {

            }
        };
        camera_texture.setSurfaceTextureListener(surfaceTextureListener);
    }

    private void openCamera() throws CameraAccessException
    {

        CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        cameraID = cameraManager.getCameraIdList()[0];
        CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraID);
        StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        imageDimentions = map.getOutputSizes(SurfaceTexture.class)[0];
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            checkForPermission();
        } else
        {
            cameraManager.openCamera(cameraID, new CameraDevice.StateCallback()
            {
                @Override
                public void onOpened(@NonNull CameraDevice camera)
                {
                    cameraDevice = camera;
                    try
                    {
                        startCameraPreview();
                    } catch (CameraAccessException e)
                    {
                        e.printStackTrace();
                    }

                }


                @Override
                public void onDisconnected(@NonNull CameraDevice camera)
                {
                    cameraDevice.close();

                }



                @Override
                public void onError(@NonNull CameraDevice camera, int error)
                {
                    cameraDevice.close();
                    cameraDevice = null;
                }
            }, null);
        }

    }



    private void startCameraPreview() throws CameraAccessException
    {
        SurfaceTexture texture = camera_texture.getSurfaceTexture();
        texture.setDefaultBufferSize(imageDimentions.getWidth(), imageDimentions.getHeight());
        Surface surface = new Surface(texture);

        captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        captureBuilder.addTarget(surface);


        cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback()
        {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession session)
            {
                if (cameraDevice == null)
                {
                    return;
                }
                cameraSession = session;
                try
                {
                    updatePreview();
                } catch (CameraAccessException e)
                {
                    e.printStackTrace();
                }


            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession session)
            {

            }
        }, null);

        imageReader = ImageReader.newInstance(imageDimentions.getWidth(),imageDimentions.getHeight(),ImageFormat.JPEG,1);

        imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener()
        {
            @Override
            public void onImageAvailable(ImageReader reader)
            {
                Image image = imageReader.acquireLatestImage();
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);



                image.close();
            }
        }, backGroundHandler);

    }



    private void updatePreview() throws CameraAccessException
    {
        if (cameraDevice == null)
        {
            return;
        }
        captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        cameraSession.setRepeatingRequest(captureBuilder.build(), null, backGroundHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.page_camera_preview, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*
        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }
    */
    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        {
            startBackgroundThread();
            if (camera_texture.isAvailable())
            {
                try
                {
                    openCamera();
                } catch (CameraAccessException e)
                {
                    e.printStackTrace();
                }

            } else
            {
                camera_texture.setSurfaceTextureListener(surfaceTextureListener);
            }
        }

    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            {

                cameraDevice.close();
                stopBackgroundThread();
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    private void stopBackgroundThread() throws InterruptedException
    {
        handlerThread.quitSafely();
        handlerThread.join();
        backGroundHandler = null;
        handlerThread = null;
    }

    private void startBackgroundThread()
    {


        handlerThread = new HandlerThread("Camera Background");
        handlerThread.start();
        backGroundHandler = new Handler(handlerThread.getLooper());

    }


    private int checkForPermission()
    {


        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA))
        {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Permission Needed")
                    .setMessage("This Permission is needed to scan cards")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                            Navigation.findNavController(getView()).navigate(camera_pageDirections.cameraPopToCollection());

                        }
                    })
                    .create().show();
        } else
        {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_CODE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
        return 0;

        // No explanation needed; request the permission


    }


    public void setView()
    {
        view = getView();
    }




    protected void takePicture() {
        if(null == cameraDevice) {
            Log.e(TAG, "cameraDevice is null");
            return;
        }
        CameraManager manager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
            Size[] jpegSizes = null;
            if (characteristics != null) {
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
            }

            ImageReader reader = ImageReader.newInstance(imageDimentions.getWidth(), imageDimentions.getHeight(), ImageFormat.JPEG, 1);
            List<Surface> outputSurfaces = new ArrayList<Surface>(2);
            outputSurfaces.add(reader.getSurface());
            outputSurfaces.add(new Surface(camera_texture.getSurfaceTexture()));
            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
            // Orientation

            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION,  Surface.ROTATION_0);

            File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "Hedron Vault");
            if (!folder.exists())
            {
                folder.mkdir();
            }
            if(folder.exists())
            {
                final String fileName = "Test";
                final File file = new File(folder.getPath() + "/testinggg.jpg");
                ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener()
                {
                    @Override
                    public void onImageAvailable(ImageReader reader)
                    {
                        Image image = null;
                        try
                        {
                            image = reader.acquireLatestImage();
                            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                            byte[] bytes = new byte[buffer.capacity()];
                            buffer.get(bytes);
                            save(bytes);
                        } catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        } finally
                        {
                            if (image != null)
                            {
                                image.close();
                            }
                        }
                    }

                    private void save(byte[] bytes) throws IOException
                    {
                        OutputStream output = null;
                        try
                        {
                            output = new FileOutputStream(file);
                            output.write(bytes);
                        } finally
                        {
                            if (null != output)
                            {
                                output.close();
                            }
                        }
                    }
                };

                reader.setOnImageAvailableListener(readerListener, backGroundHandler);
                final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback()
                {
                    @Override
                    public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result)
                    {
                        super.onCaptureCompleted(session, request, result);

                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("Card", file.getName(), requestFile);
                        Call<image_message> call = apiInterface.useImageRecognition(body);

                        call.enqueue(new Callback<image_message>()
                        {
                            @Override
                            public void onResponse(Call<image_message> call, Response<image_message> response)
                            {
                                if(file.exists())
                                {
                                    file.delete();
                                }
                                image_message reply = response.body();
                                checkCard(reply.Name);

                            }

                            @Override
                            public void onFailure(Call<image_message> call, Throwable t)
                            {

                            }
                        });


                        onResume();

                    }
                };
                cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback()
                {
                    @Override
                    public void onConfigured(CameraCaptureSession session)
                    {
                        try
                        {
                            session.capture(captureBuilder.build(), captureListener, backGroundHandler);
                        } catch (CameraAccessException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onConfigureFailed(CameraCaptureSession session)
                    {
                    }
                }, backGroundHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    private void checkCard(final String cardName)
    {
        new AlertDialog.Builder(getActivity())
                .setTitle("Card Confirmation")
                .setMessage("Is this the Card Scanned: " + cardName)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String tempCardName;
                        if(cardName.contains("Mind Rot"))
                        {
                            tempCardName = "Mind Rot";
                        }
                        else if (cardName.contains("Divination"))
                        {
                            tempCardName = "Divination";
                        }
                        else
                        {
                            tempCardName = cardName;
                        }
                        addCardToDatabase(tempCardName);
                    }
                })
                .setNegativeButton("Retry", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();


                    }
                })
                .create().show();
    }

    private void addCardToDatabase(String CardName)
    {

        int cardID;

        Call<List<card_id_fetch_data>> getCardID = apiInterface.searchForCardID(CardName);
        getCardID.enqueue(new Callback<List<card_id_fetch_data>>()
        {
            @Override
            public void onResponse(Call<List<card_id_fetch_data>> call, Response<List<card_id_fetch_data>> response)
            {
                List<card_id_fetch_data> cards = response.body();
                for (int i = 0; i < cards.size(); i++)
                {
                    addToCollection(cards.get(i).cardID);
                }

            }

            @Override
            public void onFailure(Call<List<card_id_fetch_data>> call, Throwable t)
            {

            }
        });

    }

    private void addToCollection(int cardID)
    {


        Call<ApiMessage> call = apiInterface.addCardToCollection(1, cardID, 1);

        call.enqueue(new Callback<ApiMessage>()
        {
            @Override
            public void onResponse(Call<ApiMessage> call, Response<ApiMessage> response)
            {

                MainActivity.sync();
                Toast.makeText(getContext(),"Added To Collection",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiMessage> call, Throwable t)
            {

            }
        });
    }
}
