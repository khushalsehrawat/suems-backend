# Step 1 — Use official Nginx image
FROM nginx:stable-alpine

# Step 2 — Remove the default nginx html files
RUN rm -rf /usr/share/nginx/html/*

# Step 3 — Copy your frontend files into Nginx html directory
COPY . /usr/share/nginx/html

# Step 4 — Expose port 80 (Render auto detects it)
EXPOSE 80

# Step 5 — Start nginx
CMD ["nginx", "-g", "daemon off;"]
