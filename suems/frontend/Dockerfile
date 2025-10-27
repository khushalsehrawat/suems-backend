# Use lightweight Nginx image
FROM nginx:alpine

# Remove default Nginx index page
RUN rm -rf /usr/share/nginx/html/*

# If your HTML files are directly in /frontend
COPY . /usr/share/nginx/html/

# If your HTML files are in /frontend/public or /frontend/dist
# COPY ./public /usr/share/nginx/html/
# or
# COPY ./dist /usr/share/nginx/html/

# Optional: custom Nginx config (prevents 403 and SPA reload errors)
COPY default.conf /etc/nginx/conf.d/default.conf

# Expose port 80 for Render
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
