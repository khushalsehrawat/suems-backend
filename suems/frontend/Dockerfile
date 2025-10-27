# Use lightweight Nginx image
FROM nginx:alpine

# Remove default Nginx page
RUN rm -rf /usr/share/nginx/html/*

# Copy all website files from current folder
COPY . /usr/share/nginx/html

# Set working directory to Nginx HTML root
WORKDIR /usr/share/nginx/html

# Verify that index.html exists (debugging)
RUN ls -la /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Run Nginx
CMD ["nginx", "-g", "daemon off;"]
