# Use official lightweight Nginx image
FROM nginx:alpine

# Remove the default nginx index page
RUN rm -rf /usr/share/nginx/html/*

# Copy only your frontend website files (adjust if your files are inside subfolder)
COPY . /usr/share/nginx/html/

# Expose port 80 for Render
EXPOSE 80

# Default command to run nginx
CMD ["nginx", "-g", "daemon off;"]
