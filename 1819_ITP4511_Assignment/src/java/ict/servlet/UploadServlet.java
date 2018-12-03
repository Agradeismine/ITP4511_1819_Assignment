/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.Restaurant;
import ict.db.RestaurantDB;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * A Java servlet that handles file upload from client.
 *
 * @author www.codejava.net
 */
@WebServlet(urlPatterns = {"/uploadFile", "/uploadRestIcon"})
public class UploadServlet extends HttpServlet {

    private RestaurantDB db;
    private Restaurant restaurant;

    private static final long serialVersionUID = 1L;
    private String defaultPath = "";
    private String name = "default";
    private boolean isUpdateSuccess = false;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new RestaurantDB(dbUrl, dbUser, dbPassword);

        defaultPath = getServletContext().getInitParameter("defaultPath");
    }

    // location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "upload";

    // upload settings
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    /**
     * Upon receiving file upload submission, parses the request to read upload
     * data and saves the file on disk.
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // checks if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)) {
            // if not, we stop here
            PrintWriter writer = response.getWriter();
            writer.println("Error: Form must has enctype=multipart/form-data.");
            writer.flush();
            return;
        }

        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // sets maximum size of upload file
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // sets maximum size of request (include file + form data)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // constructs the directory path to store upload file
        // this path is relative to application's directory
        String uploadPath = getServletContext().getRealPath("")
                //defaultPath
                + File.separator + UPLOAD_DIRECTORY;

        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // parses the request's content to extract file data
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                String fileName="";
                String filePath="";
                // iterates over form's fields
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields
                    if (!item.isFormField()) {
                        fileName = new File(item.getName()).getName();
                        filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);

                        // saves the file on disk
                        item.write(storeFile);
                        request.setAttribute("message", "Upload has been done successfully!");
                        request.setAttribute("fileName",
                                fileName);
                    } else {
                        String name = item.getFieldName();

                        String restId = item.getString();
                        restaurant = db.getRestaurantByRestId(Integer.parseInt(restId));
                        request.setAttribute("name", restId);
                        System.out.println("name " + restId);
                    }
                }
                restaurant.setRestIcon(fileName);
                isUpdateSuccess = db.editRestaurantRecord(restaurant);
            }
        } catch (Exception ex) {
            request.setAttribute("message", "There was an error: You have no upload the photo." );  //+ ex.getMessage()
            getServletContext().getRequestDispatcher("/message.jsp").forward(
                request, response);
        }
        // redirects client to message page
        if(!isUpdateSuccess){       //if not update success
            request.setAttribute("message", restaurant.getName()+ " icon is not update success");
            getServletContext().getRequestDispatcher("/message.jsp").forward(
                request, response);
        }else{
            response.sendRedirect("ViewOwnRestaurant.jsp");
        }

    }
}
