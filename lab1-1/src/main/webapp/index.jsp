<html>
<head>
    <title>File Uploading Form</title>
    <script src="https://hcaptcha.com/1/api.js" async defer></script>
</head>

<body>
    <h3>File Upload:</h3>
    Select a file to upload: <br />
    <form action = "file-upload" method = "post"  enctype = "multipart/form-data">
        <input type = "file" name = "file" size = "50" />
        <br/>
        <div class="h-captcha" data-sitekey="54a6c660-e9e4-485a-858e-fc74dafcee66"></div><br>
        <br />
        <input type = "submit" value = "Upload File" />
    </form>
</body>

</html>