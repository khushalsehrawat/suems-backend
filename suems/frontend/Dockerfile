# Step 1: Use lightweight Nginx image
FROM nginx:alpine

# Step 2: Remove default Nginx index page
RUN rm -rf /usr/share/nginx/html/*

# Step 3: Copy all files from current folder to Nginx html folder
COPY . /usr/share/nginx/html/

# Step 4: Expose port 80 for Render
EXPOSE 80

# Step 5: Start Nginx
CMD ["nginx", "-g", "daemon off;"]
