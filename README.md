# CoolBlogWebsite
my project for a blogging website.

**Features to implement:**

1. Database Design:
    - Design and develop an appropriate database schema to store blog posts, user information, and other relevant data.
    - Implement efficient data storage and retrieval mechanisms to support the blog website's requirements.

2. User Management:
    - Implement user authentication and authorization mechanisms for secure logins and access control.
    - Develop user registration and profile management functionalities, allowing users to update their information and manage their accounts.

3. Blog Post Management:
    - Develop modules to support blog post creation, editing, and deletion by authorized users.
    - Implement features such as categorization, tags, and search functionality to enhance content organization and discoverability.

4. Commenting System:
    - Design and create a commenting system that enables users to engage and interact with blog posts.
    - Implement moderation features to manage and control user comments, including approval and deletion functionalities.

**Important notes:**

findAll(Pageable page, Sort sorting) - accepts needed sorting as a request parameter. Available variants to include: 
    - Sorted by popularity (the number of subscribers or total number of likes, to be decided)
    - Sorted by creation date DESC

read-only view - all users. Write comments - users who are logged in (such users receive the role 'USER').
Creating a blog - user becomes an author (role AUTHOR), only the author can write new posts in his/her blog.

If people leave comments under a post - it increases the raiting of the author, as does a 'like' of a post or a comment
