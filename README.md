# Blog website
my project for a blogging app.

**Features:**

1. Database Design:
    - Connected PostgreSQL via JPA Hibernate and schema.sql.

2. User Management:
    - User authentication and authorization via Spring Security and JWT.
    - Users can become authors, can update their information

3. Blog Post Management:
    - Users with roles of Authors can create posts
    - Views are calculated with the help of Kafka



**to implement:**
1. editing posts, getting a particular post by id
2. categorization, tags, and search functionality for posts
3. comments to be created/ edited / deleted by wish of a user
4. rating, subscriptions, likes

**notes:**

findAll(Pageable page, Sort sorting) - accepts needed sorting as a request parameter. Available variants to include: 
    - Sorted by popularity (the number of subscribers or total number of likes, to be decided)
    - Sorted by creation date DESC

read-only view - all users. Write comments - users who are logged in (such users receive the role 'USER').
Creating a blog - user becomes an author (role AUTHOR), only the author can write new posts in his/her blog (done).

If people leave comments under a post - it increases the rating of the author, as does a 'like' of a post or a comment
