<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->
<#import "/spring.ftl" as spring>

<#import "commons.ftlh" as c>


<@c.page>
    <@c.post post=postId/>

    <#if user??>
        <div class="form">
            <div class="header">Write Comment</div>
            <div class="body">
                <form method="post" action="">
                    <input type="hidden" name="path" value="/post/${postId.id}"/>
                    <div class="field">
                        <div class="name">
                            <label for="text">Text</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text">${comment.text!}</textarea>
                        </div>
                        <@c.error "comment.text"/>
<#--                        <@c.error "enterForm.password"/>-->

                    <div class="button-field">
                        <input type="submit" value="Comment" onclick="this.form.submit();this.disabled=true;">
                    </div>
                </form>
            </div>
        </div>
    </#if>

    <div class="comments datatable">
        <div class="caption">Comments</div>
        <table>
            <thead>
            <tr>
                <th>Username</th>
                <th>Text</th>
            </tr>
            </thead>
            <tbody>
            <#if postId.comments?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No comments
                    </td>
                </tr>
            <#else>
                <#list postId.comments as comment>
                    <tr>
                        <td class="username">${comment.user.login}</td>
                        <td class="text">${comment.text}</td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>

</@c.page>
