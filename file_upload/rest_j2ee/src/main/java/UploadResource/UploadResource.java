package UploadResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam; 
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/files")
public class UploadResource {

    private final String upload_location = "E:\\virtual\\org\\";
    
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,@FormDataParam("file") FormDataContentDisposition fileDetail){            
        if (uploadedInputStream == null || fileDetail == null)
            return Response.status(400).entity("Invalid form data").build();
        String filePath = upload_location + fileDetail.getFileName();
        try {
            saveToFile(uploadedInputStream, filePath);
	} catch (IOException e) {
            return Response.status(500).entity("Can not save file").build();
        }
        return Response.status(200).entity("File saved"+fileDetail.getFileName()).build();
    }
    
    private void saveToFile(InputStream inStream, String target) throws IOException {	 
        int read = 0;
	byte[] bytes = new byte[1024];
	OutputStream out = new FileOutputStream(new File(target));
	while ((read = inStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
	}
	out.flush();
	out.close();
    }    
}
