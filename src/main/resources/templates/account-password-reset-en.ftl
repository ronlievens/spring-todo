<#include "template-header.ftl">
<h2>Hello!</h2>
<p>No need to worry, you can reset your password by clicking the link below:</p>
<a href="${url}/login/reset/${token}" target="_blank">${url}/login/reset</a>
<p>If you didn't request a password reset, feel free to delete this email!</p>
<#include "template-footer-en.ftl">
