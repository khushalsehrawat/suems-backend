SUEMS Frontend

How to Run

- Serve this folder using a simple static server so the origin matches backend CORS.
  - Recommended: VS Code Live Server on http://localhost:5500
  - Or any static server that serves at http://localhost:5500 or http://127.0.0.1:5501

- Start backend (Spring Boot) from the `suems` folder on port 8080. Ensure MySQL is running and credentials match `application.properties`.

- Open `frontend/login.html` in your browser via the server (not file://).
  - Set API base to `http://localhost:8080` in the input at the bottom and click Save.
  - Sign up a new account, then log in.

Notes

- The login page stores `token`, `userId`, and `email` in `localStorage`.
- The dashboard uses the JWT token to call:
  - `/api/user-energy/*`, `/api/sensors/recent`, `/api/simulator/*`.
- If you see CORS errors, confirm the frontend origin is allowed in `suems/src/main/java/com/suems/config/CorsConfig.java`.

