import React from 'react'

export default function Navbar() {
  return (
    <div>
        
        <nav class="navbar bg-dark border-bottom border-body" data-bs-theme="dark">
            <div className="container-fluid">
                <a className="navbar-brand" href="#">User Management</a>
                {/* <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
                </button> */}
                <button className="btn btn-outline-light">Add User</button>
            </div>
        </nav>

    </div>
  )
}
