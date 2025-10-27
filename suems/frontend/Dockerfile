# Use lightweight Nginx image
FROM nginx:alpine

# Remove default Nginx index page
RUN rm -rf /usr/share/nginx/html/*

# Copy frontend files from your project folder to Nginx web folder
COPY ./frontend /usr/share/nginx/html

# Expose port 80 for Render
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]


