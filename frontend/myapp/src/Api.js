import axios from "axios";

const API_URL = "http://localhost:8080/api/users"; // Spring Boot backend URL

export const getUsers = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        console.error("Error fetching users:", error);
        return [];
    }
};
